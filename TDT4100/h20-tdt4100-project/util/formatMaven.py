# Usage: mvn clean javafx:run 2>&1 | python util/formatMaven.py

from sys import stdin, stdout, stderr
from re import findall, sub, match

def is_meaningful(line):
  banned_libs = [ s.replace('.', '\\.') for s in [
    'javafx',
    'java',
    'org.apache',
    'jdk.internal',
    'org.codehaus',
    'org.openjfx'
  ]]
  
  return findall(f'\s+at (?:{"|".join(banned_libs)})\..*', line) == []

def is_stacktrace_info(line):
  return line in [
    '[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.\n',
    '[ERROR] Re-run Maven using the -X switch to enable full debug logging.\n',
    '[ERROR] \n',
    '[ERROR] For more information about the errors and possible solutions, please read the following articles:\n',
    '[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException\n'
  ]

def remove_return(line):
  return line.replace('\n', '')

def reformat(line):

  line = line.replace('[ERROR]', '[\033[1;31mERROR\033[0m]')
  line = line.replace('[INFO]', '[\033[1;34mINFO\033[0m]')

  # --- -> ───
  if findall('-{3,}', line) != []:
    line = line.replace('-', '─')

  # [row,col]:[12,3] -> [12:3]
  line = sub(
    r'\[row,col\]:\[(\d+),(\d+)\]',
    '[\033[1;31m' + r'\1:\2' + '\033[0m]',
    line)

  # Blinking caused by
  line = sub(r'Caused by: ([\w.-]+: )(.+)', r'[\033[1;5;31mCAUSED BY\033[0m] \1\033[1;31m\2\033[0m', line)

  # Object paths
  line = sub(r'((?:[A-Za-z\-]+\.){2,}[A-Za-z\-]+)', r'\033[32m\1\033[0m', line)
  # Normal paths
  line = sub(r'((?:/)?(?:[\w.-]+/){3,}[\w.-]+)', r'\033[33m\1\033[0m', line)

  # Blinking error message
  line = sub(
    r'Message: (.+)',
    '[\033[1;5;31mMESSAGE\033[0m] \033[1;31m' +  r'\1' + '\033[0m',
    line
  )

  return line

lastLineWasMeaningless=False
for line in stdin:
  if not is_stacktrace_info(line):

    if is_meaningful(line):
      lastLineWasMeaningless = False
      line = remove_return(line)
      line = reformat(line)
      print(line)
    elif not lastLineWasMeaningless:
      print('\tat [...]')
      lastLineWasMeaningless = True
