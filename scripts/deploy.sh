BRANCH="main"

echo "Deploying with branch: $BRANCH"

# shellcheck disable=SC2164
cd ~/app/library-management/

git fetch -a
git checkout $BRANCH
git pull

docker compose -f docker-compose.yml down
docker compose -f docker-compose.yml pull discoveryserver
docker compose -f docker-compose.yml up -d discoveryserver
docker system prune -af