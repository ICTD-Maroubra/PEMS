import React from "react";
import {Line, Bar, Pie} from 'react-chartjs-2';

export default class LineChart extends React.Component{

constructor(props){
  super(props);
  this.state = {
    chartData:props.chartData
    }
  }


static defaultProps = {
  displayTitle:false,
  displayLegend: false,
  legendPosition:'right'
}

  render() {
    return (
      <div class="chart">
      <Line data={this.state.chartData}
            options={{
              legend:{
                display:this.props.displayLegend
              },
              scales: {
                  yAxes: [{
                      ticks: {
                    beginAtZero: false,
                    stepSize:3,
                    max: 36
                }
            }]
        }
              }}  />
      </div>
      );
  }
}
