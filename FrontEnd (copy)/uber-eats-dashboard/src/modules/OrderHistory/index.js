import React from 'react';
import {Card, Table, Tag} from 'antd';
import orderHistory from '/home/varun/Desktop/FrontEnd/uber-eats-dashboard/src/assets/data/orders-history.json';

const OrderHistory = () => {
    const tableColumns = [
      {
        title: 'Order ID',
        dataIndex: 'orderID',
        key: 'orderID'
      },
      {
        title: 'Delivery Address',
        dataIndex: 'deliveryAddress',
        key: 'deliveryAddress'
      },
      {
        title: 'Price',
        dataIndex: 'price',
        key: 'price',
        render: (price) => `${price} $`
      },
      {
        title: 'Status',
        dataIndex: 'status',
        key: 'status',
        render: (status) => (<Tag color = {status === 'Delivered'?'green': 'red'}>{status}</Tag>)
      }
    ];

    return (
        <Card title = {'History Orders'} style = {{margin: 20}}>
            <Table
                dataSource = {orderHistory}
                columns = {tableColumns}
                rowKey = 'orderID'
            />
        </Card>
    )
};

export default OrderHistory;