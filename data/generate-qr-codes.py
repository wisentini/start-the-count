import qrcode
import json

payloads = None

with open('.\\payloads.json', 'r') as file:
    payloads = json.load(file)

payloads = sorted(payloads, key = lambda payload: (payload['file'], payload['qrCodeIndex']))

for payload in payloads:
    image = qrcode.make(str(payload['payload']))
    image.save(f".\\qr-code\\{payload['file'].split('.')[0]}_{payload['qrCodeIndex']}.png")
