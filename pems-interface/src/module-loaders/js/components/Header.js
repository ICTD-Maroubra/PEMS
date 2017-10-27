import React from "react";

import AppTitle from "./Header/AppTitle";
import UserLogin from "./Header/UserLogin";

export default class Header extends React.Component {

  render() {
    return (
      <div class="header">
      <AppTitle />
      <UserLogin />
      </div>
    );
  }
}
