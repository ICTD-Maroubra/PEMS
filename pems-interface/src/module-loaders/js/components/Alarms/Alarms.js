import React from "react";

import Accordion from "../Widgets/Accordion";
import AlarmItem from "./AlarmItem";

export default class Alarms extends React.Component {


  render() {
    const AlarmItemList = [
      <div><AlarmItem type="Warning" content="This is a warning"/></div>,
      <div><AlarmItem type="Error" content="This is an error"/></div>
    ]

    return (
      <div>
            <Accordion id={this.props.id} label="Alarms" content= {AlarmItemList}/>
      </div>
    );
  }

}
