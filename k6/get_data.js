import express from 'express';
import pkg from 'pg';
const { Client } = pkg;

const app = express();
const port = 3001;

const client = new Client({
    user: 'postgres',
    host: 'localhost',       
    database: 'faster',        
    password: 'postgres',      
    port: 5432,               
});

async function connectDB() {
  try {
      await client.connect();
      console.log('✅ PostgreSQL 연결 성공!');
  } catch (err) {
      console.error('❌ PostgreSQL 연결 실패:', err);
  }
}

await client.connect();

app.get('/random-receiver-company-manager', async (req, res) => {
  
  const result = await client.query(`
    SELECT u.*, c.* 
    FROM faster.p_user u
    JOIN faster.p_company c ON c.company_manager_id = u.id
    WHERE u.role = 'ROLE_COMPANY' 
    AND c.type = 'RECEIVER' 
    ORDER BY RANDOM() 
    LIMIT 1
  `);
  
  res.json(result.rows[0]);
});

app.get('/random-supplier-company-product', async (req, res) => {
  
  const result = await client.query(`
    SELECT c.*, p.* 
    FROM faster.p_product p
    JOIN faster.p_company c 
    ON c.id = p.company_id
    WHERE c.type = 'SUPPLIER' 
    ORDER BY RANDOM() 
    LIMIT 1
  `);

  res.json(result.rows[0]);
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});