import React from "react";

import SingleCard from "../components/Card/SingleCard";
import DoubleCards from "../components/Card/DoubleCards";
import PageTitle from "../components/Widgets/PageTitle";


export default class Dashboard extends React.Component {

constructor(){
  super();
}

render() {
    return (
      <div>
        <PageTitle title="Dashboard"/>
        <SingleCard title= "Temp. Counter" content="99" unit="°C"/>
        <DoubleCards title1= "Counter 1" content1="99"   unit1="°C"  title2= "Counter 2" content2="100" unit2="%"/>

      </div>
    );
  }
}
