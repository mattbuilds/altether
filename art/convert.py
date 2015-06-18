import os
#sos.system("inkscape blue.svg --export-png=../android/assets/640/blue_test.png -w600")

sizes = [480,640,960]
images = [['blue.svg', 'blue_test.png', 48]]

for x in images:
	print x

#command = "inkscape " + "blue.svg" + "--export-png=../android/assets/" + "640/" + "blue_test.png" + "-w600"