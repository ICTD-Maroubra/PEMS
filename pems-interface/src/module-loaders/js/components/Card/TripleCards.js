import React from "react";

import SingleCard from "./SingleCard";

export default class TripleCards extends React.Component {

  render() {
      return (
      <div>
          <div class="split3">
            <SingleCard title={this.props.title1} content={this.props.content1} unit={this.props.unit1} accId={this.props.accId1} accLabel={this.props.accLabel1} accContent={this.props.accContent1}/>
          </div>
          <div class="split3">
            <SingleCard title={this.props.title2} content={this.props.content2} unit={this.props.unit2} accId={this.props.accId2} accLabel={this.props.accLabel2} accContent={this.props.accContent2}/>
          </div>
          <div class="split3">
            <SingleCard title={this.props.title3} content={this.props.content3} unit={this.props.unit3} accId={this.props.accId3} accLabel={this.props.accLabel3} accContent={this.props.accContent3}/>
          </div>
      </div>
      );
        }
}
