FROM node:16.12.0-alpine
ENV APP_HOME=/root/dev/myapp/
WORKDIR $APP_HOME
ENV PATH /app/node_modules/.bin:$PATH
ENV NODE_ENV=production
COPY package.json ./
COPY package-lock.json ./
RUN npm --silent install
RUN npm install -g serve
COPY . ./
RUN npm --silent run build
CMD ["serve", "-s", "build"]
