from PIL import Image

img = Image.open('image.png').convert('LA')
img.save('greyscale.png')