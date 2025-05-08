'''
In this script, we want to generated easily some text for announces in order to test them later.
For that, we want to:
1- Load data:
    Open and load both french-bad-words-list-clear.txt and french-bad-words-list-crypted.txt and sava data in a list
2- Generate data:
    I've found the website https://enneagon.org/phrases that generates pretty good french sentences.
    We will generate data from there.
3- Mix up a bit the results:
    Introduce few bad-words in order to get text that should be moderated by our main moderation algorithm
4- Save the final result:
    Save data inside resources/announces-sample.csv
'''

import random
import requests
from bs4 import BeautifulSoup # Already use in previous classes
import re

url = "https://enneagon.org/phrases"
base_directory = "C:\\FakeD\\Cours\\Ingé\\SIRIUS\\Sirius"
clear_file_path = base_directory+"\\fimafeng-back\\src\\main\\resources\\moderation-files\\french-bad-words-list-clear.txt"
crypted_file_path = base_directory+"\\fimafeng-back\\src\\main\\resources\\moderation-files\\french-bad-words-list-crypted.txt"
results_file_path = base_directory+"\\fimafeng-back\\src\\test\\resources\\announces-sample.csv"

bad_words = []
sentences = []
results = []


# 1- Load data
def loadWords():
    global clear_file_path,crypted_file_path,bad_words
    try:
        with open(clear_file_path, 'r', encoding='utf-8') as file:
            for mot in file:
                bad_words.append(mot[:-1]) # removing '\n'
        with open(crypted_file_path, 'r', encoding='utf-8') as file:
            for mot in file:
                bad_words.append(mot[:-1]) # removing '\n'
    except FileNotFoundError:
        print("Error: file not found")
    except Exception as e:
        print(f"Exception occured: {e}")
    random.shuffle(bad_words)
    print(f"{len(bad_words)} words loaded")


# 2- Generate data
def generateSentences(n: int):
    global sentences
    while(len(sentences) < n):
        print(f"Not enought sentences ({len(sentences)}/{n}), sending new request")
        try:
            html_response = requests.get(url)
            html_content = html_response.text
        except requests.exceptions.RequestException as e:
            print(f"Exception occured during requests to {url}: {e}")
            exit() # if error, then no content
        soup = BeautifulSoup(html_content, 'html.parser')
        phr_tag = soup.find(id="phr")
        if not phr_tag:
            print("No tag with 'phr' as id.")
            exit()
        content = phr_tag.text.strip()
        for sentence in re.split('[.;!?]+', content): # Found at https://www.geeksforgeeks.org/python-split-multiple-characters-from-string/
            sentences.append(sentence.strip())
    print(f"{len(sentences)} sentences generated")


# 3- Mix up a bit the results
def prepareAnnounces():
    global sentences, bad_words, results

    # Adding badwords to sentences
    amount_of_sentence_to_mix_up = len(sentences)/2
    while(len(sentences)*2 > amount_of_sentence_to_mix_up):
        s1 = sentences.pop()
        l1 = s1.split(' ')
        l1.insert(random.randrange(0, len(l1)), bad_words.pop())
        s1 = ' '.join(l1)

        s2 = sentences.pop()
        l2 = s2.split(' ')
        l2.insert(random.randrange(0, len(l2)), bad_words.pop())
        s2 = ' '.join(l2)

        results.append([s1,s2,"MODERATED"])
    while(len(sentences)>=2):
        s1 = sentences.pop()
        s2 = sentences.pop()
        results.append([s1,s2, "PUBLISHED"])
    print(f"{len(results)} announces generated")


# 4- Saving the results
def saveAnnounces():
    global results_file_path, results
    try:
        with open(results_file_path, "w", encoding="utf-8") as file:
            for result in results:
                file.write(";".join(result)+"\n")
                '''
        with open(results_file_path, "r", encoding="utf-8") as file:
            print(f"{len(file)}/{len(results)} lines written")'''
    except IOError as e:
        print(f"Exception occured: {e}")
    
if __name__ == '__main__':
    loadWords()
    generateSentences(400)
    prepareAnnounces()
    saveAnnounces()
    
