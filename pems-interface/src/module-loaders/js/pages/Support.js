import React from "react";

import SingleCard from "../components/Card/SingleCard";
import PageTitle from "../components/Widgets/PageTitle";


export default class Support extends React.Component {

constructor(){
  super();
}

render() {
    return (
      <div>
        <PageTitle title="Support"/>
        <SingleCard title= "Support" content="99"/>

      </div>
    );
  }
}
