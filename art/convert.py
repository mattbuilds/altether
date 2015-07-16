import os
#sos.system("inkscape blue.svg --export-png=../android/assets/640/blue_test.png -w600")
#mac settings
ink = "/Applications/Inkscape.app/Contents/Resources/bin/inkscape "
prefix = "/users/matt/documents/dev/libgdx/box_puzzle/"

#pc settings
ink = "inkscape "
prefix ="../"

sizes = [480, 720, 960, 1440]
images = [
	['about.svg', 'about.png', 402],
	['about_text.svg', 'about_text.png', 380],
	['back.svg', 'back.png', 72],
	['back_text.svg', 'back_text.png', 148],
	['blue.svg', 'blue_tile.png', 48],
	['blue_goal.svg', 'blue_goal.png', 48],
	['box.svg', 'box.png', 48],
	['by.svg', 'by.png', 360],
	['check.svg', 'check.png', 48],
	['green.svg', 'green_tile.png', 48],
	['green_goal.svg', 'green_goal.png', 48],
	['lvl_comp.svg', 'lvl_comp.png', 288],
	['main_title.svg', 'title.png', 400],
	['menu.svg', 'menu.png', 72],
	['menu_popup.svg', 'menu_popup.png', 96],
	['more.svg', 'more.png', 402],
	['more_games.svg', 'more_games.png', 380],
	['more_text.svg', 'more_text.png', 380],
	['next.svg', 'next.png', 72],
	['next_popup.svg', 'next_popup.png', 96],
	['off_circle.svg', 'off_circle.png', 16],
	['on_circle.svg', 'on_circle.png', 16],
	['play_tile.svg', 'play.png', 402],
	['popup.svg', 'popup.png', 384],
	['red.svg', 'red_tile.png', 48],
	['red_goal.svg', 'red_goal.png', 48],
	['refresh.svg', 'refresh.png', 96],
	['tile.svg', 'tile.png', 48],
	['website.svg', 'website.png', 380],
	['yellow.svg', 'yellow_tile.png', 48],
	['yellow_goal.svg', 'yellow_goal.png', 48],

]

for x in images:
	for i, y in enumerate(sizes):
		width = str(int(float(y) / float(480) * x[2]))
		command = ink + prefix + "art/" + x[0] + " --export-png="+ prefix+"android/assets/" + str(y) + "/" + x[1] + " -w"+width
		os.system(command)

#command = "inkscape " + "blue.svg" + "--export-png=../android/assets/" + "640/" + "blue_test.png" + "-w600"