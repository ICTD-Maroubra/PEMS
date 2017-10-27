import React from "react";

import Accordion from "../Widgets/Accordion"

export default class SingleCard extends React.Component {

  render() {
    console.log(this.props);
    const balloonIcon = "../../assets/images/edited/BalloonIcon.png";
return (


<div class="card">
  <div class="card-title">
    {this.props.title}
  </div>
  <div class="card-content">
    {this.props.content}
    {this.props.unit}
  </div>
  <div>
    <Accordion id={this.props.accId} label={this.props.accLabel} content={this.props.accContent} />
  </div>
</div>


    );
  }
}
