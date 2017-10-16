import React from "react";

import SingleCard from "../components/Card/SingleCard";
import PageTitle from "../components/Widgets/PageTitle";


export default class Users extends React.Component {

constructor(){
  super();
}

render() {
    return (
      <div class="body">
        <PageTitle title="Users"/>
        <SingleCard title= "Users" content="99"/>

      </div>
    );
  }
}
