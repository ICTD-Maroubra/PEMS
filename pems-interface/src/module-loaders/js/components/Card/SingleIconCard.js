import React from "react";

export default class SingleIconCard extends React.Component {

  render() {
    console.log(this.props);
    const balloonIcon = "../../assets/images/edited/BalloonIcon.png";
        return (

        <div class="card">
          <div class="tooltip">
            <img src={balloonIcon} />
          </div>
          <div class="card-icon">
            <img class="icon" src={this.props.icon} />
          </div>
          <div class="card-icon-title">
            {this.props.title}
          </div>
          <div class="info-text">
          {this.props.tooltip}
          </div>
        </div>

            );
  }
}
