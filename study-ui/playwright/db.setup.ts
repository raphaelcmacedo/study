import { test as setup } from '@playwright/test';

const pgp = require('pg-promise');
const { db } = require('../support/db');

setup('delete posts', async () => {
    const query = 'Delete from Post';
    await db.none(query);
  });

