import React from "react";

import SideMenu from "../components/Menu/SideMenu";

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
      <div><Toggle title="Toggle 1"/></div>

    ]

    const currentPath= this.props.location.pathname;
    return (
      <div>
      <SideMenu currentPath={currentPath}/>
      <div class="body">
      <PageTitle title="Settings"/>
      <div class="flex-box">
      <SingleCard title= "Settings Subtitle 1" content={ButtonList} unit="" accContent=""/>
      </div>
      </div>
      </div>
    );
  }
}
