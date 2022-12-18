import os
import subprocess

def runCommand(command):
  process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
  output, error = process.communicate()
  return output.decode('utf-8')

def probeMimetype(filepath):
  return runCommand("file -b --mime-type " + filepath).strip()

def getMimetypes(directory):
  return ((file, probeMimetype(os.path.join(directory, file))) for file in os.listdir(directory))

def getLinkReference(filepath):
  return runCommand("readlink " + filepath)

def convertFile(input, output):
  runCommand(f'convert -background none {input} {output}')

if __name__ == '__main__':
  inputdir =  '../icons/Papirus/64x64/mimetypes'
  outputdir = './src/main/resources/graphics/filetreeicons'
  for (file, mimetype) in getMimetypes(inputdir):
    if mimetype == 'inode/symlink':
      convertFile(
        f'{inputdir}/{getLinkReference(inputdir + "/" + file)}',
        f'{outputdir}/{file[:-4]}.png'
      )
    elif mimetype == 'image/svg+xml':
      convertFile(
        f'{inputdir}/{file}',
        f'{outputdir}/{file[:-4]}.png'
      )
    else:
      print(f"[ERROR]: {file} - {mimetype}")
  
  convertFile('../icons/Papirus/64x64/places/folder-blue.svg', './src/main/resources/graphics/folder2.png')