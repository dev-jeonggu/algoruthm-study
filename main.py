import os
import re
import sys
import requests
import certifi
from bs4 import BeautifulSoup, Tag
from datetime import datetime

def extract_metadata(java_file_path):
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
    return text.strip().replace(" ", "_").replace(":", "").replace("/", "_")

def extract_programmers_markdown(soup):
    content = {
        "문제 설명": "",
        "제한사항": "",
        "입출력 예": "",
        "입출력 예 설명": ""
    }
    desc_box = soup.select_one(".markdown")
    if not desc_box:
        return content

    current = "문제 설명"
    buffer = []

    for tag in desc_box.children:
        if isinstance(tag, Tag):
            if tag.name == 'h5':
                if buffer:
                    content[current] = "\n".join(buffer).strip()
                    buffer = []
                heading = tag.get_text(strip=True)
                if heading in content:
                    current = heading
            elif tag.name == 'ul':
                for li in tag.find_all('li'):
                    buffer.append(f"- {li.get_text(strip=True)}")
            elif tag.name == 'table':
                rows = tag.find_all('tr')
                table_lines = []
                for i, row in enumerate(rows):
                    cols = [td.get_text(strip=True) for td in row.find_all(['td', 'th'])]
                    line = " | ".join(cols)
                    table_lines.append(line)
                    if i == 0:
                        table_lines.append(" | ".join(['---'] * len(cols)))
                buffer.extend(table_lines)
            elif tag.name == 'p':
                buffer.append(tag.get_text(strip=True))
            elif tag.name == 'br':
                buffer.append("\n")

    if buffer:
        content[current] = "\n".join(buffer).strip()

    return content

def fetch_baekjoon_content(url):
    try:
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
        }
        response = requests.get(url, headers=headers, verify=certifi.where(), timeout=30)
        response.raise_for_status()
    except Exception as e:
        raise Exception(f"❌ 백준 URL 접속 실패: {url}\n{str(e)}")

    soup = BeautifulSoup(response.text, 'html.parser')

    # 페이지 전체 텍스트 추출
    page_text = soup.get_text()
    lines = [line.strip() for line in page_text.split('\n') if line.strip()]

    문제 = ""
    입력 = ""
    출력 = ""
    예제입력 = ""
    예제출력 = ""
    
    # 정답 비율 이후부터 문제 설명 시작
    문제_start = -1
    문제_end = -1

    for i, line in enumerate(lines):
        # 정답 비율이 포함된 라인 찾기 (예: "83.805%")
        if re.search(r'\d+\.\d+%', line):
            문제_start = i + 1
        # "첫 번째 줄에"가 나오면 문제 설명 끝
        elif '첫 번째 줄에' in line and 문제_start != -1:
            문제_end = i
            break
    # 문제 설명 추출
    if 문제_start != -1 and 문제_end != -1:
        문제_lines = lines[문제_start:문제_end]
        문제 = ' '.join(문제_lines).strip()

    # 입력 설명 추출 ("첫 번째 줄에"부터 시작)
    입력_lines = []
    입력_found = False
    
    for i, line in enumerate(lines):
        if '첫 번째 줄에' in line:
            입력_found = True
            입력_lines.append(line)
        elif 입력_found and ('출력한다' in line or '출력하시오' in line):
            # 출력 설명까지 포함하지 않고 입력만
            break
        elif 입력_found and '번째 줄에' in line:
            입력_lines.append(line)
    
    입력 = ' '.join(입력_lines).strip()
    
    # 출력 설명 추출
    for line in lines:
        if ('출력한다' in line or '출력하시오' in line) and '경우의 수' in line:
            출력 = line.strip()
            break    
        
    # 예제 입력 찾기
    sample_input_section = soup.select_one("#sample-input-1")
    if sample_input_section:
        예제입력 = sample_input_section.get_text(strip=True)
    
    # 예제 출력 찾기
    sample_output_section = soup.select_one("#sample-output-1")
    if sample_output_section:
        예제출력 = sample_output_section.get_text(strip=True)

    # 대안으로 sampledata에서 찾기
    if not 예제입력 or not 예제출력:
        sample_data = soup.select(".sampledata")
        for sample in sample_data:
            if "예제 입력" in sample.get_text():
                예제입력 = sample.find("pre").get_text(strip=True) if sample.find("pre") else ""
            elif "예제 출력" in sample.get_text():
                예제출력 = sample.find("pre").get_text(strip=True) if sample.find("pre") else ""

    return {
        "문제": 문제,
        "입력": 입력,
        "출력": 출력,
        "예제 입력": 예제입력,
        "예제 출력": 예제출력
    }

def create_markdown(platform, title, url, content):
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
    title, platform, url = extract_metadata(java_file_path)
    if not all([title, platform, url]):
        print("❌ 주석 정보가 부족합니다. // NOTE : 문제명, 플랫폼, URL 모두 필요합니다.")
        return

    platform_dir = "programmers" if "프로그래머스" in platform else "baekjoon"

    if platform_dir == "programmers":
        try:
            headers = {
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
            }
            response = requests.get(url, headers=headers, timeout=30)
            response.raise_for_status()
            soup = BeautifulSoup(response.text, 'html.parser')
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

    create_markdown(platform_dir, title, url, content)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("사용법: python script.py <java_file_path>")
        sys.exit(1)
        
    for arg in sys.argv[1:]:
        if arg.endswith(".java"):
            main(arg)