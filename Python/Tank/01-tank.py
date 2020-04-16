import pygame, sys
import os, time
import random
from PIL import Image
from pygame.locals import *


def image_size(imagepath):
	im = Image.open(imagepath, "r")
	return im.size[0], im.size[1]


class TankMain():
	width = 600
	height = 500
	
	def start_game(self):
		"pygame init"
		pygame.init()
		screen = pygame.display.set_mode((TankMain.width, TankMain.height), 0, 32)
		pygame.display.set_caption('坦克大战')
		image_num = ''
		tank_usr = None
		enemy_tanks = []
		enemy_num = random.randint(2, 10)
		explosion = []
		while True:
			screen.fill((0, 0, 0))
			if image_num == '':
				image_info = self.start_view()  # 返回的是有关坦克信息的元组
				image_num = image_info[2]
				pass
			else:
				# image_num = self.get_event()+image_num
				if image_num == image_info[3]:
					image_num = image_num - image_info[3]
					print(image_num)
					pass
				elif image_num == -1:
					image_num = image_num + image_info[3]
					print(image_num)
				image_info = self.start_view(image_num)
				pass
			if not tank_usr:
				screen.blit(image_info[0], ((600 - image_info[1][0]) / 2, (500 - image_info[1][1]) / 2))
				for event in pygame.event.get():
					if event.type == KEYDOWN:
						if event.key == K_LEFT:
							image_num = image_num - 1
						elif event.key == K_RIGHT:
							image_num = image_num + 1
						elif event.key == K_RETURN:
							tank_usr = user_tank(screen, image_num + 1)
							enemy_tanks = [enemy_tank(screen) for i in range(1, enemy_num + 1)]
						break
					pass
				pass
			else:
				text_info = self.write_text(len(enemy_tanks), len(tank_usr.missiles))
				screen.blit(text_info[0], (0, 6))
				screen.blit(text_info[1], (0, 16))
				if len(enemy_tanks) != 0:
					for i in enemy_tanks:
						i.display_tank(i.direction)
						i.random_move()
					self.get_event(tank_usr)
					tank_usr.move()
					# print(len(tank_usr.missiles))
					if len(tank_usr.missiles) > 0:
						missile_not_miss = []
						for missile_no, missile in enumerate(tank_usr.missiles):
							if missile.Alive == True:
								missile.move()
								for tank_no, tanks in enumerate(enemy_tanks):
									if missile.rect.colliderect(tanks.rect):
										explode = Explosion(screen,tanks.rect, missile.missile_num)
										explosion.append(explode)
										del tanks
										pygame.display.update()
										del enemy_tanks[tank_no]
										missile.Alive = False
							else:
								del tank_usr.missiles[missile_no]
							pass
						for i in missile_not_miss:
							del tank_usr.missiles[i]
							pygame.display.update()
						pass
			if len(explosion) > 0:
				for i, explode in enumerate(explosion):
					if explode.Alive:
						explode.display()
					else:
						del explosion[i]
						pass
			time.sleep(0.08)
			pygame.display.update()
			if len(enemy_tanks) == 0 and tank_usr:
				self.game_end(screen)
				self.get_event(tank_usr)
			pass
		pass



	
	def start_view(self, image_num=0):
		images = os.listdir("坦克/坦克选择")
		images.sort(key=lambda l: l[l.rfind(".png") - 1:l.rfind(".png")])
		# print(images)
		tank_image = pygame.image.load("坦克/坦克选择/" + images[image_num])
		return tank_image, image_size("坦克/坦克选择/" + images[image_num]), image_num, len(images)
		
		pass
	
	def get_event(self, my_tank):
		my_tank.display_tank(my_tank.direction)
		for event in pygame.event.get():
			if event.type == QUIT:
				self.end_game()
			elif event.type == KEYDOWN:
				if event.key == K_LEFT or event.key == K_a:
					# while pygame.key.get_pressed:
					if my_tank.direction == "L":
						pass
					else:
						my_tank.display_tank("L")
					my_tank.direction = "L"
					my_tank.stop = False
					pass
				elif event.key == K_RIGHT or event.key == K_d:
					if my_tank.direction == "R":
						my_tank.move()
					else:
						my_tank.display_tank("R")
					my_tank.direction = "R"
					my_tank.stop = False
					pass
				elif event.key == K_UP or event.key == K_w:
					if my_tank.direction == "U":
						pass
					else:
						my_tank.display_tank("U")
					my_tank.direction = "U"
					my_tank.stop = False
					pass
				elif event.key == K_DOWN or event.key == K_s:
					if my_tank.direction == "D":
						pass
					else:
						my_tank.display_tank("D")
					my_tank.direction = "D"
					my_tank.stop = False
					pass
				elif event.key == K_SPACE:
					my_tank.fire()
				elif event.key == K_ESCAPE:
					self.end_game()
			
			elif event.type == KEYUP:
				if event.key == K_s or event.key == K_w or event.key == K_d or event.key == K_a \
						or event.key == K_LEFT or event.key == K_RIGHT or event.key == K_DOWN \
						or event.key == K_UP:
					my_tank.stop = True
			
			pass
		pass
	def game_end(self,screen):
		time.sleep(0.1)
		end_image = pygame.image.load("游戏结束图片/" + "GameOver.png")
		screen.blit(end_image, (175, 120))
		pygame.display.update()


	def end_game(self,screen):
		sys.exit()
		pass
	
	def write_text(self, tank_num, missile_num):
		font = pygame.font.SysFont('wenquanyizenheimono', 15)
		text_et = font.render("Enemies Left:%s" % tank_num, True, (255, 0, 0))
		text_usr_missile = font.render("User Missile Left:%s" % missile_num, True, (255, 0, 0))
		return text_et, text_usr_missile


# 坦克大战游戏中所有对象的超级父类
class basic_item(pygame.sprite.Sprite):
	def __init__(self, screen):
		pygame.sprite.Sprite.__init__(self)
		self.screen = screen
	
	def load_image(self):
		pass
	
	def reload_image(self):
		pass
	
	pass


class tank(basic_item):
	# 定义类属性
	width = 50
	height = 50
	
	def __init__(self, screen, left, top, tank_num=1):
		super().__init__(screen)
		self.stop = False  # screem为坦克在移动加载过程中需要用到的背景窗口
		self.direction = "D"  # 坦克默认方向向下
		self.speed = 10  # 坦克移动的速度
		self.images = {}
		self.images["L"] = pygame.image.load("坦克/坦克素材" + str(tank_num) + '/tankleft.png')
		self.images["D"] = pygame.image.load("坦克/坦克素材" + str(tank_num) + '/tankdown.png')
		self.images["R"] = pygame.image.load("坦克/坦克素材" + str(tank_num) + "/tankright.png")
		self.images["U"] = pygame.image.load("坦克/坦克素材" + str(tank_num) + '/tankup.png')
		self.image = self.images[self.direction]  # 坦克的图片由方向决定
		self.alive = True  # 决定坦克是否被消灭
		self.rect = self.image.get_rect()
		self.rect.left = left
		self.rect.top = top
	
	pass
	
	def display_tank(self, direction="D"):
		self.image = self.images[direction]
		self.screen.blit(self.image, self.rect)
	
	pass
	
	def move(self):
		if not self.stop:
			if self.direction == "L":
				if self.rect.left > 0:  # 判断坦克是否处于屏幕左边的边界
					self.rect.left -= self.speed
					pass
				else:
					self.stop = True
			elif self.direction == "R":
				if self.rect.right < TankMain.width:
					self.rect.left += self.speed
				else:
					self.stop = True
			elif self.direction == "U":
				if self.rect.top > 0:
					self.rect.top -= self.speed
				else:
					self.stop = True
			elif self.direction == "D":
				if self.rect.bottom < 500:
					self.rect.bottom += self.speed
				else:
					self.stop = True
				pass
		
		pass
	
	def fire(self):
		
		pass


class enemy_tank(tank):
	def __init__(self, screen, tank_num=1):
		
		super().__init__(screen, random.randint(0, 11) * 50, 150, tank_num)
		self.stop = False
		self.step = random.randint(3, 8)
	
	def random_move(self):
		if self.step <= 0 or self.stop == True:
			self.stop = True
			self.step = random.randint(3, 8)
			self.get_state()
			return
		else:
			self.move()
			print(self.rect.left, self.rect.top, self.rect.right, self.rect.bottom)
			self.step -= 1
	
	def get_state(self):
		self.stop = False
		r = random.randint(0, 4)
		if r == 4:
			self.stop = True
		elif r == 1:
			self.direction = "L"
		elif r == 2:
			self.direction = "R"
		elif r == 3:
			self.direction = "U"
		elif r == 0:
			self.direction = "D"


class user_tank(tank):
	def __init__(self, screen, tank_num=1):
		super().__init__(screen, 275, 400, tank_num)
		self.tank_num = tank_num
		self.missiles = []
		self.stop = True;
		self.speed = 15
	
	def fire(self):
		rect = pygame.Rect.copy(self.rect)
		if self.direction == "D" or self.direction == "U":
			rect.left = self.rect.left + (self.width - missiles.width) / 2 + 5
		elif self.direction == "L":
			rect.top = self.rect.top + (self.height - missiles.height) / 2
			rect.left = self.rect.left - missiles.height / 2
		elif self.direction == "R":
			rect.top = self.rect.top + (self.height - missiles.height) / 2
			rect.left = self.rect.left - missiles.height / 2
		usr_missile = normal_missiles(rect, self.screen, missile_file_num=9,
		                              missile_num=self.tank_num, missile_direction=self.direction)
		self.missiles.append(usr_missile)


class missiles(basic_item):
	width = 30
	height = 30
	
	def __init__(self, screen, rect, missile_file_num=9, missile_num=1, missile_direction='D'):
		super().__init__(screen)
		self.images = {}
		self.missile_direction = missile_direction
		self.missile_file_num = missile_file_num
		self.missile_num = missile_num
		self.images["D"] = pygame.image.load("炮弹/炮弹素材" + str(missile_file_num) + "/" + str(missile_num) + "-down.png")
		self.images["U"] = pygame.image.load("炮弹/炮弹素材" + str(missile_file_num) + "/" + str(missile_num) + "-up.png")
		self.images["L"] = pygame.image.load("炮弹/炮弹素材" + str(missile_file_num) + "/" + str(missile_num) + "-left.png")
		self.images["R"] = pygame.image.load("炮弹/炮弹素材" + str(missile_file_num) + "/" + str(missile_num) + "-right.png")
		self.image = self.images[missile_direction]
		self.rect = self.image.get_rect()
		self.rect = pygame.Rect.copy(rect)
		self.speed = 20
		self.Alive = True
	
	pass
	
	def display(self, missile_derection, rect):
		self.image = self.images[missile_derection]
		self.screen.blit(self.image, rect)
	
	def move(self):
		if self.Alive == True:
			if self.missile_direction == "D":
				if self.rect.bottom <= 500:
					self.rect.bottom += self.speed
					self.display(self.missile_direction, self.rect)
				else:
					self.Alive = False
					del self
					return
			if self.missile_direction == "U":
				if self.rect.top >= 0:
					self.rect.top -= self.speed
					self.display(self.missile_direction, self.rect)
				else:
					self.Alive = False
					del self
					return
			if self.missile_direction == "L":
				if self.rect.left >= 0:
					self.rect.left -= self.speed
					self.display(self.missile_direction, self.rect)
				else:
					self.Alive = False
					del self
					return
			if self.missile_direction == "R":
				if self.rect.right <= 600:
					self.rect.right += self.speed
					self.display(self.missile_direction, self.rect)
				else:
					self.Alive = False
					del self
		else:
			del self
		return


class normal_missiles(missiles):
	def __init__(self, rect, screen, missile_file_num=9, missile_num=1, missile_direction="D"):
		super().__init__(screen, rect, missile_file_num, missile_num, missile_direction)
		self.Alive = True
		self.speed = 80


class walls(basic_item):
	pass


class Explosion(basic_item):
	pass
	
	def __init__(self, screen, rect, missile_num=1):
		super().__init__(screen)
		self.missile_num = missile_num
		self.display_num = 1
		self.rect = rect
		self.Alive = True
	
	def display(self):
		if self.display_num <= 4:
			image = pygame.image.load("爆炸/爆炸效果" + str(self.missile_num) + "/" +
			                          str(self.display_num) + ".png")
			rect = image.get_rect()
			rect.left = self.rect.left+(tank.width-self.display_num*10)/2
			rect.top = self.rect.top +(tank.height-self.display_num*10)/2
			self.screen.blit(image, rect)
			self.display_num += 1
		else:
			self.Alive = False


if __name__ == "__main__":
	game = TankMain()
	game.start_game()
