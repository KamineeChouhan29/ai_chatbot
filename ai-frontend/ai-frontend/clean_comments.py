import os
import re

def clean_html_comments(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Regex to find all HTML comments
    # We only want to remove large comments (e.g., > 100 characters or containing newlines)
    # to preserve small functional comments like <!-- Header -->
    
    def replacer(match):
        comment_body = match.group(1)
        if '\n' in comment_body or len(comment_body) > 100:
            return '' # Remove it
        return match.group(0) # Keep it

    # (?s) makes . match newlines
    new_content = re.sub(r'<!--(.*?)-->', replacer, content, flags=re.DOTALL)
    
    # Remove multiple blank lines
    new_content = re.sub(r'\n\s*\n\s*\n', '\n\n', new_content)

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(new_content)

def main():
    base_dir = r"c:\Users\91969\Desktop\ai_chatbot\ai-frontend\ai-frontend\src\app\pages"
    for root, dirs, files in os.walk(base_dir):
        for file in files:
            if file.endswith(".html"):
                filepath = os.path.join(root, file)
                print(f"Cleaning {filepath}")
                clean_html_comments(filepath)
    
    app_html = r"c:\Users\91969\Desktop\ai_chatbot\ai-frontend\ai-frontend\src\app\app.html"
    print(f"Cleaning {app_html}")
    clean_html_comments(app_html)

if __name__ == "__main__":
    main()
