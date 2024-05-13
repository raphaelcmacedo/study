import { type FullConfig } from '@playwright/test';

const pgp = require('pg-promise');
const { db } = require('././support/db.ts');

async function globalSetup(config: FullConfig) {
    const query = 'Delete from Post';
    db.none(query);
}

export default globalSetup;