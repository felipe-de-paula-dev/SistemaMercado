FROM docker.io/library/node:22-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

EXPOSE 4200

RUN npm install -g serve
CMD ["serve", "-s", "dist/Mercado/browser", "-l", "4200"]