select product_name
from layer.orders o
         inner join layer.customers c on c.id = o.customer_id
where lower(c.name) = lower(:name);