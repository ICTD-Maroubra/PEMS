import React from "react";

import SingleCard from "../components/Card/SingleCard";
import PageTitle from "../components/Widgets/PageTitle";


export default class Emergency extends React.Component {

constructor(){
  super();
}

render() {
    return (
      <div>
        <PageTitle title="Emergency"/>
        <SingleCard title= "Emergency" content="99"/>

      </div>
    );
  }
}
