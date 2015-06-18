import os
#sos.system("inkscape blue.svg --export-png=../android/assets/640/blue_test.png -w600")
mac_ink = "/Applications/Inkscape.app/Contents/Resources/bin/inkscape "
prefix = "/users/matt/documents/dev/libgdx/box_puzzle/"

sizes = ['480','640','960']
images = [
	['blue.svg', 'blue_test.png', ['48','64','96']],
	['about_text.svg', 'about_text.png', ['380','570','760']],
]

for x in images:
	for i, y in enumerate(sizes):
		command = mac_ink + prefix + "art/" + x[0] + " --export-png="+ prefix+"/android/assets/" + y + "/" + x[1] + " -w"+x[2][i]
		os.system(command)

#command = "inkscape " + "blue.svg" + "--export-png=../android/assets/" + "640/" + "blue_test.png" + "-w600"