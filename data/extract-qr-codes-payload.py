import cv2
import os.path

from wand.image import Image
from wand.color import Color

with os.scandir(".\\pdf\\") as entries:
    for entry in entries:
        pages = Image(filename = entry.path, resolution = 300)

        for i, page in enumerate(pages.sequence):
            with Image(page) as image:
                image.format = 'png'
                image.background_color = Color('white')
                image.alpha_channel = 'remove'

                image_filename = os.path.splitext(os.path.basename(entry.name))[0]
                image_filename = '{}.png'.format(image_filename)
                image_filename = os.path.join('.\\output\\', image_filename)

                image.save(filename = image_filename)

payloads = list()

with os.scandir(".\\output\\") as entries:
    for entry in entries:
        try:
            image = cv2.imread(entry.path)
            detect = cv2.QRCodeDetector()
            value, points, straight_qrcode = detect.detectAndDecode(image)

            payloads.append({
                'file': entry.name,
                'payload': value
            })
        except:
            print('deu ruim')
