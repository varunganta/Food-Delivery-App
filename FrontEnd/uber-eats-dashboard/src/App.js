import React from 'react';
import {Layout, Image} from "antd";
import SideMenu from "/home/varun/uber-eats-dashboard/src/assets/components/SideMenu";
import AppRoutes from "/home/varun/uber-eats-dashboard/src/assets/components/AppRoutes";

const {Sider, Content, Footer} = Layout
function App() {
  return (
    <Layout>
        <Sider style = {{height: "100vh", backGroundColor: 'white'}}>
            <Image src = "https://logos-world.net/wp-content/uploads/2020/11/Uber-Eats-Symbol.jpg" preview = {false}/>
            <SideMenu/>
        </Sider>
        <Layout>
            <Content>
                <AppRoutes/>
            </Content>
            <Footer style = {{textAlign: 'center'}}>
                Uber Eats Restaurant Dashboard @2023
            </Footer>
        </Layout>
    </Layout>
  );
}


export default App;
