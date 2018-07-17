from PIL import Image

img = Image.open('creeper_specular.jpg').convert('L')
img.save('creeper_specular_g.jpg')