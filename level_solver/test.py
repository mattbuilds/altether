import pprint
import copy
import random
import json

def printRows(level):
	print "ROW"
	for row in level:
		print row

def moveLeft(level):
	for x, row in enumerate(level):
		for y, element in enumerate(row):
			if element > 0:
				checkLeft(level, x, y, element)
	return level

def checkLeft(level, x, y, element):
	if element > 10:
		space = [int(i) for i in str(element)]
		space[1] = -space[1]
	else:
		space = [element, 0]

	level[x][y] = element
	if y == 0:
		pass
	elif level[x][y-1] == -space[0]:
		level[x][y] = space[1]
		level[x][y-1] = -100
	elif level[x][y-1] < 0 and level[x][y-1] > -100:
		level[x][y] = space[1]
		space[0] = int(str(space[0]) + str(abs(level[x][y-1])))
		checkLeft(level, x, y-1, space[0])
	elif level[x][y-1] == 0:
		level[x][y] = space[1]
		checkLeft(level, x, y-1, space[0])
	elif level[x][y-1] != 0:
		pass

def moveRight(level):
	for x, row in enumerate(level):
		row_len = len(row) -1
		for y,element in enumerate(reversed(row)):
			if element > 0:
				checkRight(level, x, row_len-y, element, row_len)
	return level

def checkRight(level, x, y, element, row_len):
	if element > 10:
		space = [int(i) for i in str(element)]
		space[1] = -space[1]
	else:
		space = [element, 0]

	level[x][y] = element
	if y == row_len:
		pass
	elif level[x][y+1] == -space[0]:
		level[x][y] = space[1]
		level[x][y+1] = -100
	elif level[x][y+1] < 0 and level[x][y+1] > -100:
		level[x][y] = space[1]
		space[0] = int(str(space[0]) + str(abs(level[x][y+1])))
		checkRight(level, x, y+1, space[0], row_len)
	elif level[x][y+1] == 0:
		level[x][y] = space[1]
		checkRight(level, x, y+1, space[0], row_len)
	elif level[x][y+1] != 0:
		pass

def moveUp(level):
	for x, row in enumerate(zip(*level)):
		for y, element in enumerate(row):
			if element > 0:
				checkUp(level, x, y, element)
	return level

def checkUp(level, x, y, element):
	if element > 10:
		space = [int(i) for i in str(element)]
		space[1] = -space[1]
	else:
		space = [element, 0]

	level[y][x] = element
	if y == 0:
		pass
	elif level[y-1][x] == -space[0]:
		level[y][x] = space[1]
		level[y-1][x] = -100
	elif level[y-1][x] < 0 and level[y-1][x] > -100:
		level[y][x] = space[1]
		space[0] = int(str(space[0]) + str(abs(level[y-1][x])))
		checkUp(level, x, y-1, space[0])
	elif level[y-1][x] == 0:
		level[y][x] = space[1]
		checkUp(level, x, y-1, space[0])
	elif level[y-1][x] != 0:
		pass


def moveDown(level):
	for x, row in enumerate(zip(*level)):
		row_len = len(row) -1
		for y, element in enumerate(reversed(row)):
			if element > 0:
				checkDown(level, x, row_len-y, element, row_len)
	return level

def checkDown(level, x,  y, element, row_len):
	if element > 10:
		space = [int(i) for i in str(element)]
		space[1] = -space[1]
	else:
		space = [element, 0]

	level[y][x] = element
	if y == row_len:
		pass
	elif level[y+1][x] == -space[0]:
		level[y][x] = space[1]
		level[y+1][x] = -100
	elif level[y+1][x] < 0 and level[y+1][x] > -100:
		level[y][x] = space[1]
		space[0] = int(str(space[0]) + str(abs(level[y+1][x])))
		checkDown(level, x, y+1, space[0], row_len)
	elif level[y+1][x] == 0:
		level[y][x] = space[1]
		checkDown(level, x, y+1, space[0], row_len)
	elif level[y+1][x] != 0:
		pass

def array_to_string(level):
	result = ''
	for row in level:
		for column in row:
			result = result + str(column) + ','
	return result[:-1]

def run_sim(level, previous):
	result = array_to_string(level)

	if moves.get(result, None):
		if len(moves[result]['previous']) > len(previous):
			moves[result]['previous'] = previous
		else:
			return None
	else:
		moves[result] = {'previous': previous}

	left = moveLeft(copy.deepcopy(level))
	moves[result]['left'] = array_to_string(left)
	run_sim(copy.deepcopy(left), previous + 'L')

	right = moveRight(copy.deepcopy(level))
	moves[result]['right'] = array_to_string(right)
	run_sim(copy.deepcopy(right), previous + 'R')

	up = moveUp(copy.deepcopy(level))
	moves[result]['up'] = array_to_string(up)
	run_sim(copy.deepcopy(up), previous + 'U')

	down = moveDown(copy.deepcopy(level))
	moves[result]['down'] = array_to_string(down)
	run_sim(copy.deepcopy(down), previous + 'D')

def find_path(final, checked, count):
	for key in moves:
		if key != final:
			if moves[key]['left'] == final and moves[key]['left'] not in checked:
				count += 1
				checked[key] = count
				find_path(key, checked, count)
				#print 'left'
			if moves[key]['right'] == final and moves[key]['right'] not in checked:
				count += 1
				checked[key] = count
				find_path(key, checked, count)
				#print 'right'
			if moves[key]['up'] == final and moves[key]['up'] not in checked:
				count += 1
				checked[key] = count
				find_path(key, checked, count)
				#print 'up'
			if moves[key]['down'] == final and moves[key]['down'] not in checked:
				count += 1
				checked[key] = count
				find_path(key, checked, count)	
				#print 'down'

def print_locations(answer, boxes):
	answer_print = {}

	for x in range(boxes):
		answer_print[x+1] = {}

	for y, col in enumerate(answer):
		for x, space in enumerate(col):
			if space > 0:
				answer_print[space]['start'] = {'x':x, 'y':8-y}
			if space < 0:
				answer_print[-space]['answer'] = {'x':x, 'y':8-y}

	print json.dumps(answer_print)
	f = open('output', 'w')
	f.write(json.dumps(answer_print))

#start = [[0,0,0,0,0,0,0,0],
#		 [0,0,0,0,0,0,0,0],
#		 [0,0,0,0,0,-3,0,0],
#		 [0,0,0,0,2,0,0,0],
#		 [0,0,0,0,0,0,0,0],
#		 [0,-1,4,0,0,0,0,0],
#		 [0,0,0,0,0,3,-4,0],
#		 [1,0,-2,0,0,0,0,0]]

moves = {}
size = 9
boxes = 4

initial = [0] * (size*size)

for x in range(boxes):
	initial[x] = x+1
	initial[x+boxes] = -(x+1)

random.shuffle(initial)

start =  [[0]*size for i in range(size)]

for x, value in enumerate(initial):
	start[x/size][x%size] = value

run_sim(copy.deepcopy(start), '')

for key in moves:
	solveable = True 
	char = key.split(',')
	for number in char:
		if int(number) > 0:
			solveable =  False
	if solveable == True:
		print moves[key]['previous']
		print_locations(start, boxes)



#f = open('output', 'w')
#f.write(str(moves))