import React from "react";

import SingleIconCard from "./SingleIconCard";

export default class SuperSplit extends React.Component {

  render() {
return (
  <div>
      <div class="SuperSplit1">
        <SingleCard title={this.props.title1} content={this.props.content1} unit={this.props.unit1}/>
      </div>
      <div>
        <SingleCard title={this.props.title2} content={this.props.content2} unit={this.props.unit2}/>
      </div>
  </div>
);
  }
}
