import React from "react";

import Body from "../components/Body";
import DoubleCards from "../components/Card/DoubleCards";
import SingleCard from "../components/Card/SingleCard";
import Button from "../components/Widgets/Button";
import Toggle from "../components/Widgets/Toggle";
import PageTitle from "../components/Widgets/PageTitle";

export default class Settings extends React.Component {

constructor(){
  super();
}

render() {

    const ButtonList = [
      <div><Button key={1} title="Button 1" /></div>,
      <div><Button key={2} title="Button 2" /></div>,
      <div><Button key={3} title="Button 3" /></div>,
      <div><Button key={4} title="Button 4" /></div>
    ]

    const ToggleList =[
      <div><Toggle key={1} title="Toggle 1"/></div>

    ]

    return (
      <div>
      <PageTitle title="Settings"/>
      <DoubleCards title1= "Settings Subtitle 1" content1={ButtonList}   unit1=""  title2= "Settings Subtitle 2" content2={ToggleList} unit2=""/>
      </div>
    );
  }
}
