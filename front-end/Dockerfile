FROM node:lts AS build
WORKDIR /app
COPY . /app
RUN yarn --silent install
RUN yarn run build

FROM nginx:1.23.3
COPY --from=build /app/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
