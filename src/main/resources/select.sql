select product_name
from orders o
         inner join customers c on c.id = o.customer_id
where lower(c.name) = lower(:name);