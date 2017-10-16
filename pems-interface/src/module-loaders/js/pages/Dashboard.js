import React from "react";

import SingleCard from "../components/Card/SingleCard";
import DoubleCards from "../components/Card/DoubleCards";
import TripleCards from "../components/Card/TripleCards";
import PageTitle from "../components/Widgets/PageTitle";


export default class Dashboard extends React.Component {

constructor(){
  super();
}

render() {
    return (
      <div class="body">
        <PageTitle title="Dashboard"/>
        <SingleCard title= "Temp. Counter" content="99" unit="°C"/>
        <DoubleCards title1= "Counter 1" content1="99"   unit1="°C"  title2= "Counter 2" content2="100" unit2="%"/>
        <TripleCards title1= "Temp" content1="23"   unit1="°C" title2= "Temp" content2="23"   unit2="°C"  title3= "Counter 3" content3="20" unit3="^"/>

      </div>
    );
  }
}
