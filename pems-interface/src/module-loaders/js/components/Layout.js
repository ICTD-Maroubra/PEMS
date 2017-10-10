import React from "react";

import Footer from "./Footer";
import Header from "./Header";
import SideMenu from "./Menu/SideMenu";
import Body from "./Body";

export default class Layout extends React.Component {

render() {
    return (
      <div>
        <Header />
        <SideMenu />
        <Body />
        <Footer />
      </div>
    );
  }
}
