from pyzbar.pyzbar import decode
from PIL import Image
import json
import os

figure_directory = '.\\output\\'

files = os.listdir(figure_directory)

payloads = list()

for file in files:
    image = Image.open(f'{figure_directory}{file}')

    all_info = decode(image)

    if len(all_info) > 0:
        print(f'Detected {len(all_info)} QR codes in {file}.')

        for detected in all_info:
            payload = detected.data.decode()

            if not payload.startswith('QRBU'):
                continue

            qrCodeIndex = payload.split(' ')[0].split(':')[1]

            payloads.append({
                'file': file,
                'qrCodeIndex': qrCodeIndex,
                'payload': payload
            })

            image_filename = os.path.splitext(os.path.basename(file))[0]
            image_filename = '{}_{}.png'.format(image_filename, qrCodeIndex)
            image_filename = os.path.join('.\\qr-codes\\', image_filename)

            (x, y, w, h) = detected.rect

            image.crop((x - 25, y - 25, x + w + 25, y + h + 25)).save(image_filename)

sorted_payloads = sorted(payloads, key = lambda payload: (payload['file'], payload['qrCodeIndex']))

with open('.\\output.json', 'w') as file:
    file.write(json.dumps(sorted_payloads, indent = 4))
