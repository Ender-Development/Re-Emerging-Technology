from PIL import Image, ImageFile
from os import listdir
from sys import argv

DIR = 'textures/gui/container'

barColours = {
	(198, 198, 198, 255): 'none', # gui bg colour from vanilla
	(255, 216, 0,   255): 'power',
	(116, 116, 116, 255): 'co2',
	(0,   148, 255, 255): 'water',
	(169, 40,  28, 255):  'heat'
}

colourBars = {
	'none':  (198, 198, 198, 255),
	'power': (255, 216, 0,   255),
	'co2':   (116, 116, 116, 255),
	'water': (0,   148, 255, 255),
	'heat':  (169, 40,  28, 255)
}

skipInputIOChecks = [*map(lambda fn: DIR + '/' + fn + '_gui.png', [
	'battery',
	'waste_collector'
])]

skipOutputIOChecks = [*map(lambda fn: DIR + '/' + fn + '_gui.png', [
	'co2_diffuser',
	'fabricator',
	'battery',
	'algorithmic_optimiser',
	'hydroponic_grow_light',
	'waste_collector'
])]

def fake_print(*values: object) -> None:
	pass

if len(argv) > 1 and argv[1] == 'git-hook':
	print = fake_print

exitCode = 0

def checkBar(img: ImageFile.ImageFile, y: int) -> bool:
	global exitCode
	barTypeColour: tuple[int, int, int, int] = img.getpixel((167, y)) # type: ignore
	barType = barColours.get(barTypeColour, 'UNK ' + str(barTypeColour))
	if barType == 'none':
		return True
	if barType.startswith('UNK'):
		print(y, barType)
		exitCode = 1
	barOuterColour: tuple[int, int, int, int] = img.getpixel((167, y + 2)) # type: ignore
	if barOuterColour != (88, 88, 88, 255):
		print(y, 'outer colour', barOuterColour)
		exitCode = 1
	barInnerColour: tuple[int, int, int, int] = img.getpixel((167, y + 3)) # type: ignore
	if barInnerColour != (64, 64, 64, 255):
		print(y, 'inner colour', barOuterColour)
		exitCode = 1
	if img.getpixel((170, y)) != barTypeColour or img.getpixel((126, y + 14)) != barTypeColour:
		print(y, 'bar too smol (should go 170 -> 126)')
		exitCode = 1
	if img.getpixel((171, y)) == barTypeColour or img.getpixel((125, y + 14)) == barTypeColour:
		print(y, 'bar too big (should go 170 -> 126)')
		exitCode = 1
	return False

def checkIO(img: ImageFile.ImageFile):
	global exitCode
	if img.filename not in skipInputIOChecks  and img.getpixel((16, 35)) != (139, 139, 139, 255):
		print('(alleged) input bg colour is wrong')
		exitCode = 1
	if img.filename not in skipInputIOChecks  and img.getpixel((15, 34)) != (25, 90, 153, 255):
		print('item input position is not correct!')
		exitCode = 1
	if img.filename not in skipOutputIOChecks and img.getpixel((79, 35)) != (139, 139, 139, 255):
		print('(alleged) output bg colour is wrong')
		exitCode = 1
	if img.filename not in skipOutputIOChecks and img.getpixel((78, 34)) != (154, 90, 27, 255):
		print('item output position is not correct!')
		exitCode = 1

# couldn't come up with a better name for these lmao
def checkLittleGrayThingiesOffTheSides(img: ImageFile.ImageFile):
	global exitCode
	y = 3
	while True:
		leftSide = img.getpixel((1, y))
		if leftSide == (0, 0, 0, 255):
			break
		if leftSide != (255, 255, 255, 255) and (leftSide != (160, 160, 160, 255) or img.getpixel((2, y)) != (160, 160, 160, 255) or img.getpixel((3, y)) != (64, 64, 64, 255)):
			print(y, 'the little gray thingie off the left side is wrong')
			exitCode = 1
		rightSide = img.getpixel((173, y))
		if rightSide != (85, 85, 85, 255) and (rightSide != (43, 43, 43, 255) or img.getpixel((172, y)) != (43, 43, 43, 255) or img.getpixel((171, y)) != (64, 64, 64, 255)):
			print(y, 'the little gray thingie off the right side is wrong')
			exitCode = 1
		y += 1

def checkBGColour(img: ImageFile.ImageFile) -> bool:
	global exitCode
	if img.getpixel((4, 4)) != colourBars['none'] or img.getpixel((69, 26)) != colourBars['none']:
		print('GUI bg colour is wrong')
		exitCode = 1
		return True
	return False

def checkCorners(img: ImageFile.ImageFile):
	global exitCode
	if img.getpixel((3, 3)) != (255, 255, 255, 255) or img.getpixel((2, 2)) != (255, 255, 255, 255):
		print('top left corner doesn\'t have proper white (ffffff)')
		exitCode = 1
	if img.getpixel((172, 2)) != (198, 198, 198, 255) or img.getpixel((2, 176)) != (198, 198, 198, 255):
		print('top right/bottom left corner doesn\'t have proper gray (c6c6c6)')
		exitCode = 1
	if img.getpixel((171, 175)) != (85, 85, 85, 255) or img.getpixel((172, 176)) != (85, 85, 85, 255):
		print('bottom right corner doesn\'t have proper gray (555555)')
		exitCode = 1

for fn in listdir(DIR):
	if fn == 'creative_filler_gui.png' or not fn.endswith('_gui.png'):
		continue

	img = Image.open(DIR + '/' + fn)
	print(fn)
	if img.size != (256, 256): # sanity check
		print('is not 256x256!')
		exitCode = 1
		continue

	if checkBGColour(img):
		continue
	y = 4
	while not checkBar(img, y):
		y += 15
	checkIO(img)
	checkLittleGrayThingiesOffTheSides(img)
	checkCorners(img)
	print()

exit(exitCode)
