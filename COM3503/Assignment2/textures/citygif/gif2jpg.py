from PIL import Image

def iter_frames(im):
    try:
        i= 0
        while 1:
            im.seek(i)
            imframe = im.copy()
            if i == 0: 
                palette = imframe.getpalette()
            else:
                imframe.putpalette(palette)
            yield imframe
            i += 1
    except EOFError:
        pass

im = Image.open('night_city.gif')
for i, frame in enumerate(iter_frames(im)):
    rgb_im = frame.convert('RGB', dither = None, palette = 'ADAPTIVE')
    rgb_im.save('city%d.jpg' % i, 'JPEG', quality = 80)
im.close()