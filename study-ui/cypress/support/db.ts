const pgp = require('pg-promise');

const dbConfig = {
  dbHost: 'localhost',
  dbLogin: 'user',
  dbPassword: 'pass',
  dbName: 'study',
  dbPort: 5432,
};

const connectionString = `postgres://${dbConfig.dbLogin}:${dbConfig.dbPassword}@${dbConfig.dbHost}:${dbConfig.dbPort}/${dbConfig.dbName}`;
const db = pgp()(connectionString);

module.exports = { db };