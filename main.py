import os
import re
import sys
import requests
import certifi
from bs4 import BeautifulSoup, Tag
from datetime import datetime

def extract_metadata(java_file_path):
    """Java 파일에서 메타데이터 추출"""
    with open(java_file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    title = platform = url = None
    for line in lines:
        if "NOTE" in line:
            content = line.strip().split(":", 1)[-1].strip()
            if content.startswith("http"):
                url = content
            elif content.lower() in ["프로그래머스", "백준"]:
                platform = content.lower()
            else:
                title = content.strip()

    return title, platform, url

def slugify(text):
    """텍스트를 파일명으로 사용 가능한 형태로 변환"""
    return text.strip().replace(" ", "_").replace(":", "").replace("/", "_")

def extract_programmers_markdown(soup):
    """프로그래머스 페이지에서 마크다운 추출"""
    content = {
        "문제 설명": "",
        "제한사항": "",
        "입출력 예": "",
        "입출력 예 설명": ""
    }
    
    # 여러 가능한 선택자 시도
    desc_selectors = [
        ".markdown",
        ".lesson-content",
        ".problem-description", 
        "[class*='markdown']",
        "[class*='content']"
    ]
    
    desc_box = None
    for selector in desc_selectors:
        desc_box = soup.select_one(selector)
        if desc_box:
            print(f"✓ 설명 영역 찾음: {selector}")
            break
    
    if not desc_box:
        # 대안: 전체 텍스트에서 패턴으로 찾기
        print("⚠️ .markdown 영역을 찾을 수 없어 전체 텍스트 파싱 시도")
        full_text = soup.get_text()
        
        # 텍스트에서 각 섹션 추출
        sections = re.split(r'\n\s*(문제 설명|제한사항|입출력 예|입출력 예 설명)\s*\n', full_text)
        
        current_section = None
        for i, section in enumerate(sections):
            section_title = section.strip()
            if section_title in content:
                current_section = section_title
            elif current_section and section.strip():
                # 다음 섹션이 나오기 전까지의 내용을 수집
                content_text = section.strip()
                # 너무 긴 내용은 적당히 자르기
                if len(content_text) > 2000:
                    content_text = content_text[:2000] + "..."
                content[current_section] = content_text
                current_section = None
        
        return content

    current = "문제 설명"
    buffer = []

    # 모든 자식 요소 처리
    for element in desc_box.descendants:
        if hasattr(element, 'name'):
            if element.name == 'h5':
                # 이전 버퍼 내용 저장
                if buffer:
                    content[current] = "\n".join(buffer).strip()
                    buffer = []
                
                heading = element.get_text(strip=True)
                print(f"🔍 발견된 헤딩: '{heading}'")
                
                # 헤딩이 우리가 찾는 섹션 중 하나인지 확인
                if heading in content:
                    current = heading
                elif "문제" in heading:
                    current = "문제 설명"
                elif "제한" in heading:
                    current = "제한사항"
                elif "입출력" in heading and "예" in heading and "설명" not in heading:
                    current = "입출력 예"
                elif "입출력" in heading and "설명" in heading:
                    current = "입출력 예 설명"
                    
            elif element.name == 'p':
                text = element.get_text(strip=True)
                if text:
                    buffer.append(text)
                    
            elif element.name == 'ul':
                for li in element.find_all('li'):
                    buffer.append(f"- {li.get_text(strip=True)}")
                    
            elif element.name == 'table':
                rows = element.find_all('tr')
                table_lines = []
                for i, row in enumerate(rows):
                    cols = [td.get_text(strip=True) for td in row.find_all(['td', 'th'])]
                    line = " | ".join(cols)
                    table_lines.append(line)
                    if i == 0:
                        table_lines.append(" | ".join(['---'] * len(cols)))
                buffer.extend(table_lines)
                
            elif element.name == 'br':
                buffer.append("")

    if buffer:
        content[current] = "\n".join(buffer).strip()

    return content
    
def load_snapshot(url):
    safe = re.sub(r'[^a-zA-Z0-9]', '_', url)
    path = f"snapshots/{safe}.html"

    # 1️⃣ snapshot이 이미 있으면 그대로 사용
    if os.path.exists(path):
        print(f"✓ Snapshot loaded: {path}")
        with open(path, encoding="utf-8") as f:
            return BeautifulSoup(f, "html.parser")

    # 2️⃣ GitHub Actions에서는 절대 requests 금지
    if os.getenv("GITHUB_ACTIONS") == "true":
        raise Exception(f"❌ Snapshot not found in CI: {path}")

    # 3️⃣ 로컬에서만 fallback 다운로드
    print(f"📥 Local fallback download: {url}")
    headers = {
        "User-Agent": (
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
            "AppleWebKit/537.36 (KHTML, like Gecko) "
            "Chrome/91.0.4472.124 Safari/537.36"
        ),
        "Accept-Language": "ko-KR,ko;q=0.9",
    }

    response = requests.get(url, headers=headers, timeout=30)
    response.raise_for_status()

    os.makedirs("snapshots", exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(response.text)

    return BeautifulSoup(response.text, "html.parser")
        
def fetch_baekjoon_content(url):
    """백준 페이지에서 문제 내용 추출 - HTML 구조 기반 파싱"""
    try:
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
        }
        response = requests.get(url, headers=headers, verify=certifi.where(), timeout=30)
        response.raise_for_status()
    except Exception as e:
        raise Exception(f"❌ 백준 URL 접속 실패: {url}\n{str(e)}")

    soup = BeautifulSoup(response.text, 'html.parser')
    
    # 초기화
    문제 = ""
    입력 = ""
    출력 = ""
    예제입력 = []
    예제출력 = []
    
    # 1. 문제 영역 찾기 (id="problem_description")
    problem_section = soup.find('div', {'id': 'problem_description'})
    if problem_section:
        # 모든 p 태그의 텍스트를 합치기
        문제_paragraphs = problem_section.find_all('p')
        문제 = '\n'.join([p.get_text(strip=True) for p in 문제_paragraphs])
        print(f"✓ 문제 설명 찾음: {len(문제)}자")
    
    # 2. 입력 영역 찾기 (id="problem_input")
    input_section = soup.find('div', {'id': 'problem_input'})
    if input_section:
        입력_paragraphs = input_section.find_all('p')
        입력 = '\n'.join([p.get_text(strip=True) for p in 입력_paragraphs])
        print(f"✓ 입력 설명 찾음: {len(입력)}자")
    
    # 3. 출력 영역 찾기 (id="problem_output")
    output_section = soup.find('div', {'id': 'problem_output'})
    if output_section:
        출력_paragraphs = output_section.find_all('p')
        출력 = '\n'.join([p.get_text(strip=True) for p in 출력_paragraphs])
        print(f"✓ 출력 설명 찾음: {len(출력)}자")
    
    # 4. 예제 입출력 찾기
    # 예제 입력 찾기 (class="sampledata")
    sample_inputs = soup.find_all('pre', {'class': 'sampledata', 'id': lambda x: x and x.startswith('sample-input-')})
    for sample in sample_inputs:
        text = sample.get_text(strip=True)
        if text:
            예제입력.append(text)
            print(f"✓ 예제 입력 찾음: {text}")
    
    # 예제 출력 찾기
    sample_outputs = soup.find_all('pre', {'class': 'sampledata', 'id': lambda x: x and x.startswith('sample-output-')})
    for sample in sample_outputs:
        text = sample.get_text(strip=True)
        if text:
            예제출력.append(text)
            print(f"✓ 예제 출력 찾음: {text}")
    
    # 만약 위 방법으로 못 찾았으면 다른 방법 시도
    if not 예제입력 or not 예제출력:
        # 모든 pre 태그 확인
        all_pre = soup.find_all('pre')
        for i, pre in enumerate(all_pre):
            text = pre.get_text(strip=True)
            # copy 버튼이 있는 pre 태그는 예제일 가능성이 높음
            if pre.find_next_sibling('button') or pre.find_previous_sibling('button'):
                copy_button_text = str(pre.find_next_sibling()) + str(pre.find_previous_sibling())
                if '복사' in copy_button_text:
                    if i % 2 == 0:  # 짝수 인덱스는 입력
                        예제입력.append(text)
                    else:  # 홀수 인덱스는 출력
                        예제출력.append(text)
    
    # 텍스트 기반 백업 방법
    if not 문제 or not 입력 or not 출력:
        print("⚠️ HTML 구조로 못 찾아서 텍스트 기반 파싱 시도")
        
        # 전체 텍스트 가져오기
        full_text = soup.get_text()
        
        # 문제, 입력, 출력 섹션 찾기
        sections = re.split(r'\n(문제|입력|출력|예제 입력|예제 출력)', full_text)
        
        current_section = None
        for i, section in enumerate(sections):
            section = section.strip()
            if section == '문제':
                current_section = '문제'
            elif section == '입력':
                current_section = '입력'
            elif section == '출력':
                current_section = '출력'
            elif section == '예제 입력':
                current_section = '예제 입력'
            elif section == '예제 출력':
                current_section = '예제 출력'
            elif current_section and section:
                if current_section == '문제' and not 문제:
                    문제 = section
                elif current_section == '입력' and not 입력:
                    입력 = section
                elif current_section == '출력' and not 출력:
                    출력 = section
                elif current_section == '예제 입력' and not 예제입력:
                    예제입력.append(section)
                elif current_section == '예제 출력' and not 예제출력:
                    예제출력.append(section)
    
    # 결과 정리
    result = {
        "문제": 문제.strip() if 문제 else "",
        "입력": 입력.strip() if 입력 else "",
        "출력": 출력.strip() if 출력 else "",
        "예제 입력": 예제입력[0] if 예제입력 else "",
        "예제 출력": 예제출력[0] if 예제출력 else ""
    }
    
    # 디버깅 정보
    print("\n📋 최종 추출 결과:")
    for key, value in result.items():
        if value:
            print(f"  {key}: {value[:50]}..." if len(value) > 50 else f"  {key}: {value}")
        else:
            print(f"  {key}: ❌ 없음")
    
    # 필수 항목 확인
    if not result["문제"]:
        print("\n⚠️ 문제 설명을 찾지 못했습니다. 페이지 구조 확인 필요")
    if not result["예제 입력"] or not result["예제 출력"]:
        print("\n⚠️ 예제를 찾지 못했습니다. 수동으로 입력해야 할 수 있습니다.")
    
    return result

def create_markdown(platform, title, url, content):
    """마크다운 파일 생성"""
    if platform == "programmers":
        # 프로그래머스: problems/programmers/문제명/문제명.md
        dir_name = slugify(title)
        base_path = f"problems/programmers/{dir_name}"
        md_filename = f"{dir_name}.md"
    else:
        # 백준: problems/baekjoon/문제번호_문제명/문제명.md (문제번호 제외)
        dir_name = slugify(title)  # 31995_게임말올려놓기
        base_path = f"problems/baekjoon/{dir_name}"
        
        # 문제명에서 번호 부분 제거 (31995_게임말올려놓기 -> 게임말올려놓기)
        if "_" in title:
            problem_name = title.split("_", 1)[1]  # 첫 번째 언더스코어 이후 부분
        else:
            problem_name = title
        
        md_filename = f"{slugify(problem_name)}.md"
    
    os.makedirs(base_path, exist_ok=True)
    md_path = f"{base_path}/{md_filename}"

    if platform == "programmers":
        md_content = f"""\
# {title}

> [문제 링크]({url})

## 문제 설명
{content.get('문제 설명', '')}

## 제한사항
{content.get('제한사항', '')}

## 입출력 예
{content.get('입출력 예', '')}

## 입출력 예 설명
{content.get('입출력 예 설명', '')}
"""
    else:
        md_content = f"""\
# {title}

> [문제 링크]({url})

## 문제
{content.get('문제', '')}

## 입력
{content.get('입력', '')}

## 출력
{content.get('출력', '')}

## 예제 입력
```
{content.get('예제 입력', '')}
```

## 예제 출력
```
{content.get('예제 출력', '')}
```
"""

    with open(md_path, "w", encoding="utf-8") as f:
        f.write(md_content)
    print(f"✅ Markdown 생성 완료: {md_path}")

def main(java_file_path):
    """메인 함수"""
    print(f"🚀 처리 시작: {java_file_path}")
    
    # 메타데이터 추출
    title, platform, url = extract_metadata(java_file_path)
    if not all([title, platform, url]):
        print("❌ 주석 정보가 부족합니다.")
        print("필요한 형식:")
        print("// NOTE : 1541_잃어버린괄호")
        print("// NOTE : 백준")
        print("// NOTE : https://www.acmicpc.net/problem/1541")
        return

    platform_dir = "programmers" if "프로그래머스" in platform else "baekjoon"
    print(f"📋 플랫폼: {platform_dir}, 제목: {title}")

    if platform_dir == "programmers":
        try:
            soup = load_snapshot(url)
            
            content = extract_programmers_markdown(soup)
        except Exception as e:
            print(f"❌ 프로그래머스 페이지 접근 실패: {e}")
            return
    else:
        try:
            content = fetch_baekjoon_content(url)
        except Exception as e:
            print(f"❌ 백준 콘텐츠 추출 실패: {e}")
            return

    # 마크다운 생성
    create_markdown(platform_dir, title, url, content)

def find_missing_markdown_files():
    """마크다운이 없는 Java 파일들을 찾는 함수"""
    missing_files = []
    
    if not os.path.exists("problems"):
        print("❌ problems 폴더가 존재하지 않습니다.")
        return missing_files
    
    # problems 폴더의 모든 Java 파일 찾기
    for root, dirs, files in os.walk("problems"):
        for file in files:
            if file.endswith(".java"):
                java_path = os.path.join(root, file)
                
                # 같은 디렉토리에 .md 파일이 있는지 확인
                md_files = [f for f in files if f.endswith(".md")]
                
                if not md_files:
                    missing_files.append(java_path)
                    print(f"📝 마크다운 누락: {java_path}")
                else:
                    print(f"✅ 마크다운 존재: {java_path}")
    
    return missing_files

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("🔍 마크다운이 없는 Java 파일들을 찾는 중...")
        missing_files = find_missing_markdown_files()
        
        if missing_files:
            print(f"\n🎯 {len(missing_files)}개의 파일을 처리합니다:")
            for java_file in missing_files:
                print(f"\nProcessing: {java_file}")
                main(java_file)
            print(f"\n✅ 총 {len(missing_files)}개 파일 처리 완료!")
        else:
            print("✅ 모든 Java 파일에 마크다운이 존재합니다.")
        
    else:
        # 특정 파일 처리
        for arg in sys.argv[1:]:
            if arg.endswith(".java"):
                main(arg)
