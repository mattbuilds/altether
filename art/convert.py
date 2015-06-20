import os
#sos.system("inkscape blue.svg --export-png=../android/assets/640/blue_test.png -w600")
#mac settings
ink = "/Applications/Inkscape.app/Contents/Resources/bin/inkscape "
prefix = "/users/matt/documents/dev/libgdx/box_puzzle/"

#pc settings
ink = "inkscape "
prefix ="../"

sizes = ['480','640','960']
images = [
	['about.svg', 'about.png', ['402','603','804']],
	['about_text.svg', 'about_text.png', ['380','570','760']],
	['back.svg', 'back.png', ['72','108','144']],
	['back_text.svg', 'back_text.png', ['148','222','296']],
	['blue.svg', 'blue_tile.png', ['48','64','96']],
	['box.svg', 'box.png', ['48','64','96']],
	['by.svg', 'by.png', ['360','540','720']],
	['check.svg', 'check.png', ['48','64','96']],
	['by.svg', 'by.png', ['360','540','720']],
	['check.svg', 'check.png', ['48','64','96']],
	['green.svg', 'green_tile.png', ['48','64','96']],
	['grenn_goal.svg', 'green_goal.png', ['48','64','96']],
	['lvl_comp.svg', 'lvl_comp.png', ['288','432','576']],
	['next.svg', 'next.png', ['72','108','144']],
]

for x in images:
	for i, y in enumerate(sizes):
		command = ink + prefix + "art/" + x[0] + " --export-png="+ prefix+"android/assets/" + y + "/" + x[1] + " -w"+x[2][i]
		os.system(command)

#command = "inkscape " + "blue.svg" + "--export-png=../android/assets/" + "640/" + "blue_test.png" + "-w600"