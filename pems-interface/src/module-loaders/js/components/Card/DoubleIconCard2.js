import React from "react";

import SingleIconCard from "./SingleIconCard";

export default class DoubleIconCards2 extends React.Component {

  render() {
      return (
      <div>
          <div class="split3">
            <SingleIconCard icon={this.props.icon1} title={this.props.title1} tooltip={this.props.tooltip1}/>
          </div>
          <div class="split66">
            <SingleIconCard icon={this.props.icon2} title={this.props.title2} tooltip={this.props.tooltip2}/>
          </div>
      </div>
);
  }
}
