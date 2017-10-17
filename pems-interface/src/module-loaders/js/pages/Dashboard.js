import React from "react";

import SingleCard from "../components/Card/SingleCard";
import DoubleCards from "../components/Card/DoubleCards";
import TripleCards from "../components/Card/TripleCards";
import PageTitle from "../components/Widgets/PageTitle";
import LineChart from "../components/Charts/LineChart";


export default class Dashboard extends React.Component {

  constructor(){
    super();
    this.state = {
      chartData:{}
      }
    }

    componentWillMount(){
      this.getChartData();
    }

  getChartData(){
    //AJAX CALL HERE
    this.setState({
      chartData:{
          labels: ['R1', 'R2', 'R3', 'R4', 'R5', 'R6'],
          datasets: [
                      {
                        label:"temp data",
                        data:[
                              4,
                              2,
                              3,
                              6,
                              5,
                              7
                              ],
                        borderColor: [
                        'rgb(78, 94, 106)'
                        ]
                      }
                    ]
      }
    });
  }

render() {

  const InlineChart = [
    <div><LineChart chartData={this.state.chartData} /></div>
  ]

    return (
      <div>
        <PageTitle title="Dashboard"/>

        <TripleCards title1= "Counter 1" content1="23"   unit1="°C" title2= "Counter 2" content2="23"   unit2="%"  title3= "Counter 3" content3="20" unit3="^"/>
        <div class="split1"> <SingleCard title= "Temperature Graph" content={InlineChart} unit=""/></div>
        <DoubleCards title1= "Counter 4" content1="99"   unit1="°C"  title2= "Counter 5" content2="100" unit2="%"/>


      </div>
    );
  }
}
