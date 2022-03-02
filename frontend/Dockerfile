FROM node:16.12.0-alpine
ENV APP_HOME=/root/dev/myapp/
WORKDIR $APP_HOME
COPY package.json ./
COPY package-lock.json ./
RUN npm --silent install
COPY . ./
CMD ["npm", "run", "start"]
