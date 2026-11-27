
# Étape 1 : construire l'application
FROM node:26-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY build.js ./
COPY frontend/ ./frontend/

RUN npm run build

# Étape 2 : servir l'application avec Nginx
FROM nginx:alpine

COPY --from=build /app/dist/ /usr/share/nginx/html/
COPY frontend/actuator/health /usr/share/nginx/html/actuator/health

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
