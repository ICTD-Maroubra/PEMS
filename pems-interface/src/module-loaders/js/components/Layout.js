import React from "react";
import { Link } from "react-router";

import Footer from "./Footer";
import Header from "./Header";
import SideMenu from "./Menu/SideMenu";

export default class Layout extends React.Component {

constructor(){
  super();
}

render() {
    return (
      <div>
      <Header />
      {this.props.children}
      <Footer />
      </div>
    );
  }
}
