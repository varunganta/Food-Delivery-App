import React from 'react';
import {Layout, Image} from "antd";
import SideMenu from "/home/varun/Desktop/FrontEnd/uber-eats-dashboard/src/assets/components/SideMenu";
import AppRoutes from "/home/varun/Desktop/FrontEnd/uber-eats-dashboard/src/assets/components/AppRoutes";

const {Sider, Content, Footer} = Layout
function App() {
  return (
    <Layout>
        <Sider style = {{height: "100vh", backGroundColor: 'white'}}>
            <Image src = "https://play-lh.googleusercontent.com/wNnp-2QE5Zqmis2YAQ4Vhs9vjWDE_fk3_yP3dj-RFhLn6vsSTElfr3ioUvZY2535yg=w240-h480-rw" preview = {false}/>
            <SideMenu/>
        </Sider>
        <Layout>
            <Content>
                <AppRoutes/>
            </Content>
            <Footer style = {{textAlign: 'center'}}>
                Nom Nom Delivery Restaurant Dashboard @2023
            </Footer>
        </Layout>
    </Layout>
  );
}


export default App;
