import React from "react";

import Accordion from "../Widgets/Accordion"

export default class SingleCard extends React.Component {

  render() {
    const balloonIcon = "../../assets/images/edited/BalloonIcon.png";

    const contentUnitClass = this.props.unit === "" ? "hideClass" : "card-unit";
        return (


        <div class="card">
          <div class="card-title">
            {this.props.title}
          </div>
          <div class="card-content">
            {this.props.content}
            <span class={contentUnitClass}>
            {this.props.unit}
            </span>
          </div>
          <div>
            <Accordion id={this.props.accId} label={this.props.accLabel} content={this.props.accContent} />
          </div>
        </div>


            );
          }
}
