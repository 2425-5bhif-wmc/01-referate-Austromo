FROM node:23-alpine AS build

# Set working directory
WORKDIR /kc-demo-frontend

# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy application files
COPY . .

# Build the application
RUN npx webpack --node-env production

# Use an Nginx image to serve the kc-demo-frontend in production
FROM nginx:1.27-alpine
COPY --from=build /kc-demo-frontend/dist /usr/share/nginx/html

# Expose port 80 for HTTP traffic
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
