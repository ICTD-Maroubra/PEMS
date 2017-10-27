import React from "react";

import SideMenu from "../components/Menu/SideMenu";

import SingleCard from "../components/Card/SingleCard";
import PageTitle from "../components/Widgets/PageTitle";


export default class Users extends React.Component {

constructor(){
  super();
}

render() {
  const currentPath= this.props.location.pathname;
  return (
    <div>
    <SideMenu currentPath={currentPath}/>
      <div class="body">
        <PageTitle title="Users"/>
        <SingleCard title= "Users" content="99"/>
</div>
      </div>
    );
  }
}
