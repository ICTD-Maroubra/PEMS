import React from "react";

export default class Accordion extends React.Component {


  render() {
    const accordionClass = this.props.content === "" ? "hideClass" : "tab";
    return (
      <div class={accordionClass}>
            <input id={this.props.id} type="checkbox" name="tabs" class="accordion-input"/>
            <label class="accordion-label" for={this.props.id}>{this.props.label}</label>
            <div class="tab-content">
            <div class="accordion-content-box">
              {this.props.content}
              </div>
            </div>
      </div>
    );
  }

}
